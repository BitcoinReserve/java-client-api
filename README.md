Bitcoin Reserve Java SOAP Client
================================

This software enables your application to send SOAP messages to BitcoinReserve.ch and receive responses.
It requires credentials for an active BitcoinReserve.ch Business Account obtainable through the 
[website](https://bitcoinreserve.ch).

Build the Library
-----------------

This software requires that 3rd party packages commons-lang-2.5.jar, cxf-2.6.1.jar, jackson-all-1.9.6.jar, 
neethi-3.0.2.jar, wsdl4j-1.6.2.jar, xml-resolver-1.2.jar, and xmlschema-core-2.0.2.jar are available in the classpath.

Usage
-----

	// Connect to Bitcoin Reserve's Testnet server
    BitcoinReserveClient bitcoinReserve = new BitcoinReserveClient(BitcoinReserveClient.TESTNET_SERVER);
    
    // Supply your credentials
    bitcoinReserve.setUser(yourAccountNumber);
    bitcoinReserve.setApiKey(yourApiKey);
    
    // Test connectivity and credentials by querying the current exchange rate
    System.out.println(bitcoinReserve.ticker());

Additional Information
----------------------


See a [live demonstration in PHP](https://testnet.bitcoinreserve.ch/demo)  
This project is based on [Cyclos3's](http://www.cyclos.org/cyclos3/features/) 
[Web Services](http://www.cyclos.org/wiki/index.php/Web_services)  



