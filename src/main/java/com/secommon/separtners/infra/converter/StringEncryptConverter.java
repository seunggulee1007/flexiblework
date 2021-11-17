package com.secommon.separtners.infra.converter;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class StringEncryptConverter implements AttributeConverter<String, String> {

    private final StringEncryptor stringEncryptor;

//    @Autowired
//    @Qualifier("jasyptStringEncryptor")
//    public void setStringEncryptor(StringEncryptor encryptor) {
//        this.stringEncryptor = encryptor;
//    }


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
