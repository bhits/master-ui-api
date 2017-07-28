package gov.samhsa.c2s.masteruiapi.service.mapping.customconverter;


import gov.samhsa.c2s.masteruiapi.infrastructure.dto.TelecomDto;
import gov.samhsa.c2s.masteruiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.masteruiapi.service.mapping.Use;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import gov.samhsa.c2s.masteruiapi.service.mapping.System;

@Component
public class TelecomsToWorkEmailConverter extends AbstractConverter<UmsUserDto, String> {
    @Override
    protected String convert(UmsUserDto source) {
        return source.getTelecoms().stream()
                .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(System.EMAIL.toString())
                        && telecomDto.getUse().equalsIgnoreCase(Use.WORK.toString()))
                .map(TelecomDto::getValue)
                .findFirst()
                .orElse(null);
    }
}
