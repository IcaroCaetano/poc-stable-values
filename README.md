# poc-stable-values

A ideia central:

Um valor que será definido uma única vez, de forma thread-safe, e depois permanecerá imutável e compartilhável para sempre.

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