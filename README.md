# Pseudonymization Service

Pseudonymisation is a foundational technique to mitigate data protection risks. The EU’s personal data protection legislation defines pseudonymisation as the processing of personal data in such a way that this data can no longer be attributed to a specific individual, without the use of additional information.

This library partially builds on the work published as an [AWS sample](https://github.com/aws-samples/pseudonymization-service). 


## Features

Currently this library offers the following pseudonymization techniques:
 
 * keyed-hash based
 * encryption based

Planned to add
 
 * format-preserving encryption based

## Quick Start

### Maven configuration

Add the Maven dependency

```xml
<dependency>
      <groupId>com.trivadis.dataplatform.privacy</groupId>
      <artifactId>pseudonymization-core</artifactId>
      <version>${version}</version>
</dependency>
```

### Using the Pseudonymization service

There are two classes, one for each pseudonymization technique. Upon instantiation, the parameters such as secret key(s) need to be passed using the `SecureConfig` class.

#### Encryption based Pseudonymization

Encryption based Pseudonymization technique needs a secret key (256bit) and a nonce key (64bit). 

```java
private static final String SECRET_KEY = "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=";
private static final String NONCE_KEY = "2hfbSR4JxbE=";
private static final Integer NONCE_BEGIN_POS = 9;
private static final Integer NONCE_END_POS = 21;

SecureConfig secureConfig = new SecureConfig(SECRET_KEY, NONCE_KEY, NONCE_BEGIN_POS, NONCE_END_POS);

Pseudonymizer pseudonymizer = new EncryptionPseudonymizer(secureConfig);
```


#### Keyed-Hash based Pseudonymization

The Keed-Hash based Pseudonymization technique needs just a secret key (256bit). 

```java
private static final String SECRET_KEY = "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=";

SecureConfig secureConfig = new SecureConfig(SECRET_KEY);

Pseudonymizer = new KeyedHashPseudonymizer(secureConfig);
```

#### Using it from Spring Boot



