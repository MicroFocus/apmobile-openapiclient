# Project Name
AppPulse Mobile Open API - Client demo

The AppPulse Mobile Open API enables advanced customers to collect data from AppPulse Mobile  
and use it for custom needs. For example, you can use the Open API for custom reports, custom  
integrations with external systems, and custom analysis of data.  
This project is a demo for a client that use AppPulse Mobile Open API and demonstrate the way to achieve   
any relevant information from it.  
This example contains 4 steps:
  
 1. Authorise that get a token that will be use at all others steps      
 2. Get applications list     
 3. Get fundex scores for all applications      
 4. Get Matrix of device-OS performance for the second application  

[AppPulse Mobile](https://saas.hpe.com/software/AppPulse-mobile)

##Documentation
-See source Javadoc for code documentation
-More detailed docummentation is availble under the following link: [AppPulse Mobile Open API Guide](https://saas.hpe.com/sites/default/files/resources/files/AppPulse_Mobile_Open_API_Guide_1.pdf)

## Installation

-Clone the code
-Compile
-Use your "tenant id", "client id", "client secret" from AppPulse Settings section.

## Usage
java OpenApiDemo \<tenant id> \<client id> \<client secret> \<from date>  
You should input 4 parameters:  
	Tenant ID, Client ID and Client Secret that can be taken from AppPulse settings.  
	And From time: the first day period that the data will be shown.  
     e.g. 17728942 17728942#C1 9292f441-04b5-46f3-87f6-e4eda6d5451f 2015-08-02  


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## Credits
Meir Ron
Michael Seldin

## License

Distrebuted under Apache License 2.0 see LICENSE file for more details.