package ayd2.ps2026.demo.wallet.mappers;

import ayd2.ps2026.demo.wallet.dtos.response.WalletDTO;
import ayd2.ps2026.demo.wallet.models.Wallet;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WalletMapper {
    WalletDTO walletToWalletDto(Wallet wallet);
}
