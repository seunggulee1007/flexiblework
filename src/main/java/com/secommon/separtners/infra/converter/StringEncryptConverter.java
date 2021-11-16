package com.secommon.separtners.infra.converter;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class StringEncryptConverter implements AttributeConverter<String, String> {

    private static StringEncryptor stringEncryptor;

    @Autowired
    @Qualifier("jasyptStringEncryptor")
    public void setStringEncryptor(StringEncryptor encryptor) {
        StringEncryptConverter.stringEncryptor = encryptor;
    }


    @Override
    public String convertToDatabaseColumn ( String attribute ) {
        return Optional.ofNullable(attribute)
                .filter( s -> !s.isEmpty() )
                .map( StringEncryptConverter.stringEncryptor::encrypt )
                .orElse( "" );
    }

    @Override
    public String convertToEntityAttribute ( String dbData ) {
        return Optional.ofNullable(dbData)
                .filter(s -> !s.isEmpty())
                .map(StringEncryptConverter.stringEncryptor::decrypt)
                .orElse("");
    }

}
