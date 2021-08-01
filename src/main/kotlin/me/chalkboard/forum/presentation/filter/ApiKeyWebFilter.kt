package me.chalkboard.forum.presentation.filter

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class ApiKeyWebFilter(val config: ApiKeyConfig): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if(exchange.request.path.toString().contains("actuator")) {
            return chain.filter(exchange)
        }

        val uri = exchange.request.queryParams.getFirst("apikey")
        if(!uri.isNullOrBlank() && uri == config.apikey) {
            return chain.filter(exchange)
        }
        return writeUnauthorizedResponse(exchange)
    }

    private fun writeUnauthorizedResponse(exchange: ServerWebExchange): Mono<Void> {
        val response: ServerHttpResponse = exchange.response
        val body = "{\"message\":\"API_KEY認証が必要です。\"}"
        return writeResponse(response, HttpStatus.UNAUTHORIZED, body)
    }
    private fun writeResponse(response: ServerHttpResponse, status: HttpStatus, jsonBody: String): Mono<Void> {
        response.statusCode = status
        response.headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
        val dbf = response.bufferFactory()
        return response.writeWith(Mono.just(dbf.wrap(jsonBody.toByteArray())))
    }
}