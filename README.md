# marklogic-java-client-ssl

### SSL infrastructure setup

#### Create keystore.jks using keytool

```bash
keytool -genkey -alias client -keyalg RSA -keystore keystore.jks
Enter keystore password:
Re-enter new password:
What is your first and last name?
  [Unknown]:  www.marklogic.com
What is the name of your organizational unit?
  [Unknown]:  Support
What is the name of your organization?
  [Unknown]:  MarkLogic Corporation
What is the name of your City or Locality?
  [Unknown]:  San Carlos
What is the name of your State or Province?
  [Unknown]:  California
What is the two-letter country code for this unit?
  [Unknown]:  US
Is CN=www.marklogic.com, OU=Support, O=MarkLogic Corporation, L=San Carlos, ST=California, C=US correct?
  [no]:  yes
```

#### Convert the keystore file into a PKCS #12 file

```
keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -deststoretype PKCS12
Importing keystore keystore.jks to keystore.p12...
Enter destination keystore password:
Re-enter new password:
Enter source keystore password:
Entry for alias client successfully imported.
Import command completed:  1 entries successfully imported, 0 entries failed or cancelled
```

#### Create the RSA PRIVATE KEY and CERTIFICATE for configuring SSL on MarkLogic

```
openssl pkcs12 -in keystore.p12 -nodes
Enter Import Password:
MAC verified OK
Bag Attributes
[RSA PRIVATE KEY and CERTIFICATE information will be listed below]
```
