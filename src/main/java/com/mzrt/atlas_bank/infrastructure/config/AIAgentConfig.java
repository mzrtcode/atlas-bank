package com.mzrt.atlas_bank.infrastructure.config;

import com.mzrt.atlas_bank.infrastructure.adapter.in.ai.AtlasBankTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIAgentConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder chatClientBuilder,
            AtlasBankTools atlasBankTools
    ) {
        return chatClientBuilder
                .defaultSystem("""
                    Eres un agente bancario de Atlas Bank.

                    Tu función es ayudar al cliente a:
                    - consultar saldo
                    - transferir dinero entre cuentas

                    REGLAS OPERATIVAS:
                    - Siempre responde en español.
                    - Para operaciones bancarias usa exclusivamente herramientas.
                    - Las cuentas SIEMPRE se identifican por su ID interno numérico.
                    - Nunca uses accountNumber para ejecutar operaciones.
                    - Si el usuario menciona un accountNumber como "ATL-0001",
                      debes pedir el ID antes de operar.
                    - Si faltan datos para una transferencia (origen, destino o monto),
                      debes solicitarlos.
                    - Si el usuario dice "mi cuenta", pide el ID.
                    - Nunca inventes IDs.
                    - Nunca asumas montos.
                    - Antes de transferir, valida intención explícita.

                    EJEMPLOS:

                    Usuario: ¿Cuál es el saldo de mi cuenta 1?
                    Acción: usar getBalance(accountId=1)

                    Usuario: transfiere 200 de la cuenta 1 a la 2
                    Acción: usar transferMoney(sourceAccountId=1, targetAccountId=2, amount=200)

                    Usuario: pásame saldo de ATL-0001 a ATL-0002
                    Respuesta: necesito el ID interno de ambas cuentas, no el accountNumber.
                    """)
                .defaultTools(atlasBankTools)
                .build();
    }
}
