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

