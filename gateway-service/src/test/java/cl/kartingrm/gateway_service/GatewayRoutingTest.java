package cl.kartingrm.gateway_service;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.net.InetSocketAddress;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GatewayRoutingTest {

    private static HttpServer server;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/api/clients/abc", exchange -> {
            exchange.sendResponseHeaders(404, -1);
            exchange.close();
        });
        server.start();
        String baseUrl = "http://localhost:" + server.getAddress().getPort();
        registry.add("spring.cloud.discovery.client.simple.instances.client-service[0].uri", () -> baseUrl);
        registry.add("spring.cloud.discovery.client.simple.instances.pricing-service[0].uri", () -> baseUrl);
        registry.add("spring.cloud.discovery.client.simple.instances.reservation-service[0].uri", () -> baseUrl);
    }

    @AfterAll
    void tearDown() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void clientRouteReturns404() {
        webTestClient.get().uri("/api/clients/abc")
                .exchange()
                .expectStatus().isNotFound();
    }
}
