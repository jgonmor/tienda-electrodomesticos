package com.jgonmor.cart_service.service.appliance_in_cart;

import com.jgonmor.cart_service.dto.ApplianceDTO;
import com.jgonmor.cart_service.dto.ApplianceQuantityDTO;
import com.jgonmor.cart_service.model.ApplianceCart;
import com.jgonmor.cart_service.model.Cart;
import com.jgonmor.cart_service.repository.IApplianceApi;
import com.jgonmor.cart_service.repository.IApplianceInCartRepository;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ApplianceInCartService implements IApplianceInCartService {

    private final IApplianceApi applianceRepository;
    private final IApplianceInCartRepository applianceInCartRepository;
    private final Retry retry;
    private final CircuitBreakerFactory<?, ?> cbFactory;

    public ApplianceInCartService(IApplianceApi applianceRepository,
                                  IApplianceInCartRepository applianceInCartRepository,
                                  RetryRegistry retryRegistry,
                                  CircuitBreakerFactory<?, ?> cbFactory) {
        this.applianceRepository = applianceRepository;
        this.applianceInCartRepository = applianceInCartRepository;
        this.retry = retryRegistry.retry("appliance-service");
        this.cbFactory = cbFactory;
    }

    @Override
    public List<ApplianceQuantityDTO> getAppliancesInCart(Long cartId) {

        List<ApplianceCart> appliancesCart = applianceInCartRepository.findByCartId(cartId);

        System.out.println("Lista de apliances en el cart: " + appliancesCart);
        appliancesCart.forEach(applianceCart -> System.out.println("Id: " + applianceCart.getId()));


        return cbFactory.create("appliance-service")
                        .run(() -> Retry.decorateSupplier(retry, () -> {

                                                              List<Long> appliancesIds = appliancesCart.parallelStream()
                                                                                                       .map(ApplianceCart::getApplianceId)
                                                                                                       .collect(Collectors.toList());
                                                              List<ApplianceDTO> appliances = applianceRepository.getAppliancesInListId(appliancesIds);

                                                              Map<Long, ApplianceDTO> appliancesMap = appliances.parallelStream()
                                                                                                                .collect(Collectors.toMap(ApplianceDTO::getId,
                                                                                                                                 appliance -> appliance));
                                                              List<ApplianceQuantityDTO> appliancesInCart = appliancesCart.parallelStream()
                                                                                                                             .map(applianceCart -> {
                                                                                                                                 ApplianceQuantityDTO applianceQuantity = new ApplianceQuantityDTO(
                                                                                                                                         cartId,
                                                                                                                                         appliancesMap.get(applianceCart.getApplianceId()),
                                                                                                                                         applianceCart.getQuantity());
                                                                                                                                 applianceQuantity.setTotal(applianceCart.getQuantity() * applianceQuantity.getAppliance()
                                                                                                                                                                                  .getPrice());
                                                                                                                                 return applianceQuantity;
                                                                                                                             })
                                                                                                                             .collect(Collectors.toList());


                                                              return appliancesInCart;


                                                          })
                                        .get(),
                             throwable -> fallbackGetAppliancesInCart(cartId,
                                                                      throwable));
    }

    @Override
    public ApplianceQuantityDTO addApplianceToCart(Cart cart,
                                                   ApplianceQuantityDTO applianceQuantityDTO) {

        ApplianceCart exists = applianceInCartRepository.findByCartIdAndApplianceId(cart.getId(),
                                                                                    applianceQuantityDTO.getAppliance()
                                                                                                        .getId())
                                                        .orElse(null);
        if (exists != null) {
            exists.setQuantity(exists.getQuantity() + applianceQuantityDTO.getQuantity());
            ApplianceCart result = applianceInCartRepository.save(exists);
            return new ApplianceQuantityDTO(applianceQuantityDTO.getId(),
                                            applianceQuantityDTO.getAppliance(),
                                            applianceQuantityDTO.getQuantity());
        }
        ApplianceCart toSave = applianceQuantityDTO.toModel();

        return addApplianceToCart(cart,
                                  toSave);
    }

    @Override
    public ApplianceQuantityDTO addApplianceToCart(Cart cart,
                                                   ApplianceCart applianceCart) {
        applianceCart.setCart(cart);

        applianceInCartRepository.save(applianceCart);

        ApplianceQuantityDTO result = new ApplianceQuantityDTO();
        result.setId(applianceCart.getId());
        return cbFactory.create("appliance-service")
                        .run(() -> Retry.decorateSupplier(retry,
                                                          () -> {

                                                              ApplianceDTO applianceDTO = applianceRepository.getApplianceById(applianceCart.getApplianceId());
                                                              System.out.println("Precio Dto: " + applianceDTO.getPrice());

                                                              result.setAppliance(applianceDTO);
                                                              result.setQuantity(applianceCart.getQuantity());
                                                              result.setTotal(applianceDTO.getPrice() * applianceCart.getQuantity());

                                                              System.out.println("Total QuantityDto: " + result.getTotal());
                                                              return result;
                                                          })
                                        .get(),
                             throwable -> fallbackSaveAppliancesInCart(applianceCart.getId(),
                                                                       throwable));
    }


    @Override
    public void removeApplianceFromCart(Long cartId,
                                        Long applianceId) {

        applianceInCartRepository.deleteByCartIdAndApplianceId(cartId,
                                                               applianceId);

    }

    @Override
    public ApplianceQuantityDTO updateApplianceInCart(ApplianceQuantityDTO applianceQuantityDTO) {

        ApplianceCart applianceCart = applianceInCartRepository.getReferenceById(applianceQuantityDTO.getId());
        applianceCart.setQuantity(applianceQuantityDTO.getQuantity());

        ApplianceCart result = applianceInCartRepository.save(applianceCart);

        ApplianceQuantityDTO resultDTO = new ApplianceQuantityDTO();
        return cbFactory.create("appliance-service")
                        .run(() -> Retry.decorateSupplier(retry,
                                                          () -> {

                                                              ApplianceDTO applianceDTO = applianceRepository.getApplianceById(result.getApplianceId());
                                                              resultDTO.setId(result.getId());
                                                              resultDTO.setAppliance(applianceDTO);
                                                              resultDTO.setQuantity(result.getQuantity());
                                                              resultDTO.setTotal(applianceDTO.getPrice() * result.getQuantity());

                                                              return resultDTO;
                                                          })
                                        .get(),
                             throwable -> fallbackSaveAppliancesInCart(applianceCart.getId(),
                                                                       throwable));


    }

    @Override
    public ApplianceDTO getApplianceById(Long applianceId) {
        return applianceRepository.getApplianceById(applianceId);
    }


    private List<ApplianceQuantityDTO> fallbackGetAppliancesInCart(Long id,
                                                                   Throwable throwable) {

        List<ApplianceQuantityDTO> appliancesInCart = new ArrayList<>();
        appliancesInCart.add(
                new ApplianceQuantityDTO(id,
                                         new ApplianceDTO(0L,
                                                          "Error",
                                                          Arrays.toString(throwable.getStackTrace()),
                                                          throwable.getMessage(),
                                                          0),
                                         1)
        );
        return appliancesInCart;
    }

    private ApplianceQuantityDTO fallbackSaveAppliancesInCart(Long id,
                                                              Throwable throwable) {
        ApplianceQuantityDTO applianceQuantityDTO = new ApplianceQuantityDTO();
        applianceQuantityDTO.setId(id);
        applianceQuantityDTO.setAppliance(new ApplianceDTO(0L,
                                                           "Error",
                                                           Arrays.toString(throwable.getStackTrace()),
                                                           throwable.getMessage(),
                                                           0));
        return applianceQuantityDTO;
    }
}
