package arquitectura.software.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
