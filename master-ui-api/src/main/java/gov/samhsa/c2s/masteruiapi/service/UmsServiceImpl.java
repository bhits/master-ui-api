package gov.samhsa.c2s.masteruiapi.service;

import gov.samhsa.c2s.masteruiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.masteruiapi.service.dto.FullProfileResponse;
import gov.samhsa.c2s.masteruiapi.service.dto.UserDto;
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
    public FullProfileResponse getFullProfile(String userAuthId) {
        UmsUserDto currentUser = umsClient.getUserByAuthId(userAuthId);
        UserDto userDto = modelMapper.map(currentUser, UserDto.class);
        return buildFullProfileResponse(userDto);
    }


    /**
     * Builds a FullProfileResponse object from a UserDto
     * <p>
     * NOTE: The FullProfileResponse object is built using lombok's Builder method
     * @see lombok.Builder
     *
     * @param userDto - The UserDto from which to build the FullProfileResponse object
     * @return The built FullProfileResponse object
     */
    private FullProfileResponse buildFullProfileResponse(UserDto userDto) {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsClient.getLocales();

        return FullProfileResponse.builder()
                .userId(userDto.getId())
                .userAuthId(userDto.getUserAuthId())
                .userLocale(userDto.getLocale())
                .supportedLocales(supportedLocales)
                .firstName(userDto.getFirstName())
                .middleName(userDto.getMiddleName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .genderCode(userDto.getGenderCode())
                .socialSecurityNumber(userDto.getSocialSecurityNumber())
                .homeAddress(userDto.getHomeAddress())
                .homeEmail(userDto.getHomeEmail())
                .homePhone(userDto.getHomePhone())
                .roles(userDto.getRoles())
                .identifiers(userDto.getIdentifiers())
                .registrationPurposeEmail(userDto.getRegistrationPurposeEmail())
                .mrn(userDto.getMrn())
                .build();
    }
}
