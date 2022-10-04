package com.example.demo.config;

import com.example.demo.filter.ExampleWebFilter;
import com.example.demo.filter.WebClientFilters;
import io.netty.handler.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.function.Function;

@Configuration
public class WebClientConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WebClientConfiguration.class);



    @Bean
    public ExchangeFilterFunction demoLambdaFilter() {
        ExchangeFilterFunction filterFunction = (clientRequest, nextFilter) -> {
            LOG.info("WebClient demoLambdaFilter executed");
            return nextFilter.exchange(clientRequest);
        };
        return filterFunction;
    }

    @Bean
    public Function<String, ExchangeFilterFunction> headerFilterFunction() {
        return down -> (clientRequest, exchangeFunction) -> Mono.deferContextual(Mono::just)
                .flatMap(context -> {
                    ClientRequest newRequest = ClientRequest
                            .from(clientRequest)
                            .headers(httpHeaders -> {
                                System.out.println("FOO Filter 4: headerFilterFunction");
                                if (context.hasKey("APA")) {
                                    System.out.println("FOO headerFilterFunction FOUND");
                                    httpHeaders.add("NEWE_APA", context.get("APA"));
                                }
                            })
                            .build();
                    return exchangeFunction.exchange(newRequest);
                }) ;

    }

    /*
    This filter adds the header
     */
    @Bean
    public ExchangeFilterFunction headerFilter() {
        return (clientRequest, exchangeFunction) -> Mono.deferContextual(Mono::just)
                .flatMap(context -> {
                    System.out.println("FOO Filter 3: headerFilter");

                    ClientRequest newRequest = ClientRequest
                            .from(clientRequest)
                            .headers(httpHeaders -> {
                                if (context.hasKey("Authorization")) {
                                   httpHeaders.add("Authorization", context.get("Authorization"));
                                }
                            })
                            .build();
                    return exchangeFunction.exchange(newRequest);
                }) ;

    }


    @Bean
    public WebClient jsonWebClient(ExchangeFilterFunction demoLambdaFilter,
                                   Function<String, ExchangeFilterFunction> headerFilterFunction,
                                   ExchangeFilterFunction headerFilter) {

        HttpClient httpClient =
                HttpClient.create()
                        .wiretap("reactor.netty.http.client.HttpClient",
                                LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL);

        return WebClient.builder()
               // .filter(WebClientFilters.demoFilter())
               /* .filter(new ExchangeFilterFunction() {
                    @Override
                    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
                        return Mono.deferContextual(Mono::just)
                                .flatMap(context -> {


                                    System.out.println(context.toString());


                                    ClientRequest clientReq = ClientRequest.from(clientRequest)
                                            .header("APA_JJJ", "XXX")
                                            .build();

                                    return exchangeFunction.exchange(clientReq);
                                });
                    }
                })*/
            //see: https://github.com/jetty-project/jetty-reactive-httpclient
            //.clientConnector(new JettyClientHttpConnector())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(demoLambdaFilter);
                    exchangeFilterFunctions.add(headerFilter);
                    exchangeFilterFunctions.add(headerFilterFunction.apply("APA"));
                })
                .defaultHeader("X-TEST-HEADER", "AJA")

            .codecs(
                    clientCodecConfigurer ->{
                        // use defaultCodecs() to apply DefaultCodecs
                        // clientCodecConfigurer.defaultCodecs();

                        // alter a registered encoder/decoder based on the default config.
                        // clientCodecConfigurer.defaultCodecs().jackson2Encoder(...)

                        // Or
                        // use customCodecs to register Codecs from scratch.
                        clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder());
                        clientCodecConfigurer.customCodecs().register(new Jackson2JsonEncoder());
                    }

            )
            .exchangeStrategies(ExchangeStrategies.withDefaults())
            //                .exchangeFunction(ExchangeFunctions.create(new ReactorClientHttpConnector())
            //                        .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {})))
            //                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {clientRequest.}))
           //  .defaultHeaders(httpHeaders -> httpHeaders.
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build();
    }

}