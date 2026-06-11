# poc-stable-values

A ideia central:

Um valor que será definido uma única vez, de forma thread-safe, e depois permanecerá imutável e compartilhável para sempre.


## Estrutura

````
└───src
    ├───main
        ├───java
          └───com
              └───project
                  └───poc_stable_values
                      │   PocStableValuesApplication.java
                      │   
                      ├───api
                      │   └───response
                      │           FraudAnalysisResult.java
                      │           
                      ├───domain
                      │       FraudAnalysis.java
                      │       FraudStatus.java
                      │       
                      ├───dto
                      │       FraudAnalysisRequest.java
                      │       
                      ├───engine
                      │       FraudEngine.java
                      │       
                      ├───model
                      │       FaceMatchModel.java
                      │       LivenessModel.java
                      │       
                      ├───security
                      │       JwtPublicKey.java
                      │       JwtValidator.java
                      │       
                      └───service
                               FraudAnalysisService.java
````

## O conceito de Stable Value

```
private final StableValue<FraudModel> model =
        StableValue.of();
```

e

```
public FraudModel getModel() {

    return model.orElseSet(
            this::loadModel
    );
}
```

A semântica é:

```
Ainda não existe valor?
    cria

Já existe?
    reutiliza
```

### Por que não usar simplesmente final?


Imagine:

```
private final FraudModel model =
        loadModel();
```

O modelo é criado no startup.
Mesmo que nunca seja utilizado.

### Já com Stable Value:

```
private final StableValue<FraudModel> model =
        StableValue.of();
```

O modelo só será criado quando alguém realmente precisar dele.


O Java não criou Stable Values apenas para lazy loading.


#### O objetivo é garantir:

1. Computação única

Mesmo que 100 threads façam:

```
model.orElseSet(...)
```

ao mesmo tempo.


Somente uma thread executará:
```
loadModel()
```

#### 2 - Publicação segura

Depois que o valor é criado:

```
FraudModel model
```


todas as threads enxergam exatamente a mesma instância.

Sem:

```
volatile
```


```
synchronized
```

```
Lock
```

3. Imutabilidade lógica
Após definido:

## Objetivo de uso

Todos esses objetos:

- custam caro para carregar;

- são lidos milhares de vezes;

- raramente mudam;

- precisam ser compartilhados por várias threads.


### Output

````text
=== First Request ===
Loading Face Match Model...Thread: Thread[#3,main,5,main]
Loading Liveness Model...Thread: Thread[#3,main,5,main]
Loading JWT Public Key...Thread:Thread[#3,main,5,main]
Validating JWT using key kid-001 Token eyJhbGciOiJSUzI1NiIsImtpZCI6ImtpZC0wMDEifQ.eyJzdWIiOiIxMjM0NTY3ODkwMSIsIm5hbWUiOiJJY2FybyBDYWV0YW5vIiwiaWF0IjoxNzE1NjAwMDAwfQ.signature
FraudAnalysis{
    analysisId='f7913d70-afe6-4466-9814-61904c37d4a0',
    cpf='12345678901',
    faceMatchScore=70,61,
    livenessScore=89,09,
    status=MANUAL_REVIEW,
    createdAt=2026-06-11T01:39:32.618494900Z
}


=== Second Request ===
Validating JWT using key kid-001 Token eyJhbGciOiJSUzI1NiIsImtpZCI6ImtpZC0wMDEifQ.eyJzdWIiOiIxMjM0NTY3ODkwMSIsIm5hbWUiOiJJY2FybyBDYWV0YW5vIiwiaWF0IjoxNzE1NjAwMDAwfQ.signature
FraudAnalysis{
    analysisId='847cd0e5-b8df-466e-a37d-5c3b08f89c55',
    cpf='12345678901',
    faceMatchScore=94,83,
    livenessScore=77,33,
    status=MANUAL_REVIEW,
    createdAt=2026-06-11T01:39:32.640706Z
}
````