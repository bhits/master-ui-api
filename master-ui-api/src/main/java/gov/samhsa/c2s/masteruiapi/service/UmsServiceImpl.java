package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.masteruiapi.service.dto.LimitedProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UmsServiceImpl implements UmsService {

    private final UmsClient umsClient;
    private final ModelMapper modelMapper;

    @Autowired
    public UmsServiceImpl(UmsClient umsClient, ModelMapper modelMapper) {
        this.umsClient = umsClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public LimitedProfileResponse getProfile(String userAuthId, String currentUsername) {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsClient.getLocales();
        UmsUserDto currentUser = umsClient.getUserByAuthId(userAuthId);

        return LimitedProfileResponse.builder()
                .userLocale(currentUser.getLocale())
                .supportedLocales(supportedLocales)
                .username(currentUsername)
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .birthDate(currentUser.getBirthDate())
                .mrn(currentUser.getMrn())
                .build();
    }
}
