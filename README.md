# Sample client for AppPulse Mobile's Open API

The AppPulse Mobile Open API enables advanced users (and premium customers) to extract mobile app usage info and user experience data for their mobile apps from AppPulse Mobile programmatically. One could use this extracted data to generate customized reports, implement integrations with external systems and run customized analytics.
      
This project is a sample client-side implementation that uses AppPulse Mobile Open API to extract information.    

The sample contains 4 steps:  
  
 1. Authenticate using your AppPulse Mobile OpenAPI credentials and receive a token that will be used at subsequent steps   
 2. Retrieve your applications list  
 3. Retrieve fundex scores for all your applications      
 4. Retrieve the Matrix of device-OS performance for the second application in the applications list  

## Documentation
* Details around all operations possible with the OpenAPI can be found in the [AppPulse OpenAPI Browser](https://apppulse-mobile.saas.hpe.com/mobile/openapi/apibrowser/index.html)   
* Detailed documentation is available in the [AppPulse Mobile Open API Guide](http://apppulse-help.saas.hpe.com/mobile/eng/Content/4_Open_API/open_API.htm#API_Specifications)

## Installation

1. Clone the repo (git clone git@github.com:MicroFocus/apmobile-openapiclient.git)  
2. Run Maven - Install (mvn install). This generates the packaged jar named "apmobile-client-standalone.jar"   
3. Run the program by providing your "tenant id", "client id" and "client secret" (taken from AppPulse Settings section).

## Usage
java -jar apmobile-client-standalone.jar --tenantId \<tenant id> --clientId \<client id> --secret \<client secret> --fromDate \<from date in yyyy-mm-dd format>  
Mandatory parameters:  
* tenantId - required for authentication. Taken from AppPulse Mobile settings.  
* clientId - required for authentication. Taken from AppPulse Mobile settings.  
* secret - required for authentication. Taken from AppPulse Mobile settings.   
* fromDate - the start of the period from which you would like to pull data. To be provided in yyyy-mm-dd format  

Optional parameter:
* proxy - proxy hostname and port (proxyhost:port)


For example:  
     
     java -jar apmobile-client-standalone.jar --tenantId 17728942 --clientId 17728942#c1 --secret 17728942#C1 9292f441-04b5-46f3-87f6-e4eda6d5451f --fromDate 2018-06-12    


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## Credits
Shreyas Dhareshwar  
Meir Ron  
Michael Seldin

## License

Distrebuted under Apache License 2.0 see LICENSE file for more details.