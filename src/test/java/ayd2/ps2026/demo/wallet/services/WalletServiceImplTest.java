package ayd2.ps2026.demo.wallet.services;

import ayd2.ps2026.demo.auth.jwt.CustomUserDetails;
import ayd2.ps2026.demo.wallet.dtos.request.AddAmountDTO;
import ayd2.ps2026.demo.wallet.dtos.response.WalletDTO;
import ayd2.ps2026.demo.wallet.mappers.WalletMapper;
import ayd2.ps2026.demo.wallet.models.Wallet;
import ayd2.ps2026.demo.wallet.repositories.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    private static final Integer USER_ID = 1;

    private static final Float INITIAL_AMOUNT = 100f;
    private static final Float ADD_AMOUNT = 50f;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletMapper walletMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private WalletServiceImpl walletService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    void configureContext(){
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication())
                .thenReturn(authentication);
    }

    @Test
    void createWallet_success() {
        // Arrange
        Wallet wallet =
                new Wallet(USER_ID, 0f);

        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(wallet);

        // Act
        Wallet result =
                walletService.createWallet(USER_ID);

        // Assert
        assertAll(
                () -> verify(walletRepository)
                        .save(any(Wallet.class)),

                () -> assertEquals(USER_ID, result.getUserId()),

                () -> assertEquals(0f, result.getCurrency())
        );
    }

    @Test
    void getWallet_existingWallet_success() {
        configureContext();
        // Arrange
        CustomUserDetails customUserDetails =
                new CustomUserDetails(
                        USER_ID,
                        "user",
                        List.of()
                );

        Wallet wallet =
                new Wallet();

        wallet.setUserId(USER_ID);

        when(authentication.getPrincipal())
                .thenReturn(customUserDetails);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.of(wallet));

        // Act
        Wallet result =
                walletService.getWallet();

        // Assert
        assertAll(
                () -> verify(walletRepository)
                        .findByUserId(USER_ID),

                () -> verify(walletRepository, never())
                        .save(any()),

                () -> assertEquals(wallet, result)
        );
    }

    @Test
    void getWallet_walletNotExists_createWallet() {
        configureContext();
        // Arrange
        CustomUserDetails customUserDetails =
                new CustomUserDetails(
                        USER_ID,
                        "user",
                        List.of()
                );

        Wallet wallet =
                new Wallet(USER_ID, 0f);

        when(authentication.getPrincipal())
                .thenReturn(customUserDetails);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.empty());

        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(wallet);

        // Act
        Wallet result =
                walletService.getWallet();

        // Assert
        assertAll(
                () -> verify(walletRepository)
                        .findByUserId(USER_ID),

                () -> verify(walletRepository)
                        .save(any(Wallet.class)),

                () -> assertEquals(USER_ID, result.getUserId())
        );
    }

    @Test
    void addToWallet_success() {
        configureContext();
        // Arrange
        CustomUserDetails customUserDetails =
                new CustomUserDetails(
                        USER_ID,
                        "user",
                        List.of()
                );

        Wallet wallet =
                new Wallet();

        wallet.setUserId(USER_ID);
        wallet.setCurrency(INITIAL_AMOUNT);

        AddAmountDTO addAmountDTO =
                new AddAmountDTO();

        addAmountDTO.setAmount(ADD_AMOUNT);

        WalletDTO walletDTO =
                new WalletDTO();

        when(authentication.getPrincipal())
                .thenReturn(customUserDetails);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.of(wallet));

        when(walletMapper.walletToWalletDto(wallet))
                .thenReturn(walletDTO);

        // Act
        WalletDTO result =
                walletService.addToWallet(addAmountDTO);

        // Assert
        assertAll(
                () -> verify(walletRepository)
                        .save(wallet),

                () -> verify(walletMapper)
                        .walletToWalletDto(wallet),

                () -> assertEquals(
                        INITIAL_AMOUNT + ADD_AMOUNT,
                        wallet.getCurrency()
                ),

                () -> assertEquals(walletDTO, result)
        );
    }
}