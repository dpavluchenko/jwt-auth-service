package com.pavliuchenko.jwtauthservice.service.key;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
public class RsaKeyService implements KeyService {

    private KeyPair keyPair;

    @PostConstruct
    private void init() {
        try {
            keyPair = create(readKeyFile("rsa-keys/public_key.der"), readKeyFile("rsa-keys/private_key.der"));
        } catch (Exception e) {
            throw new RuntimeException("Can't read rsa keys from files", e);
        }
    }

    @Override
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    @Override
    public Mono<byte[]> getEncodedPublicKey() {
        return Mono.just(keyPair.getPublic().getEncoded());
    }

    @Override
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    private KeyPair create(byte[] publicKey, byte[] privateKey) throws GeneralSecurityException {
        X509EncodedKeySpec publicSpec =
                new X509EncodedKeySpec(publicKey);
        PKCS8EncodedKeySpec privateSpec =
                new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return new KeyPair(keyFactory.generatePublic(publicSpec), keyFactory.generatePrivate(privateSpec));
    }

    private byte[] readKeyFile(String key) throws IOException {
        Path path = new ClassPathResource(key).getFile().toPath();
        return Files.readAllBytes(path);
    }

}