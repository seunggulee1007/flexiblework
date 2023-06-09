package com.secommon.separtners.infra.converter;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class StringEncryptConverter implements AttributeConverter<String, String> {

    private final StringEncryptor stringEncryptor;

    @Override
    public String convertToDatabaseColumn ( String attribute ) {
        return Optional.ofNullable(attribute)
                .filter( s -> !s.isEmpty() )
                .map( this.stringEncryptor::encrypt )
                .orElse( "" );
    }

    @Override
    public String convertToEntityAttribute ( String dbData ) {
        return Optional.ofNullable(dbData)
                .filter(s -> !s.isEmpty())
                .map(this.stringEncryptor::decrypt)
                .orElse("");
    }

}
