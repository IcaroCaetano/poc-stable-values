# poc-stable-values

The central idea:

A value that will be defined only once, in a thread-safe manner, and then remain immutable and shareable forever.


# Objective

Allow lazy initialization of a value, ensuring that it is created only once, in a thread-safe manner, and with safe publication to all threads.

## Structure

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

## The concept of Stable Value

```
private final StableValue<FraudModel> model = StableValue.of();
```

and

```
public FraudModel getModel() {

    return model.orElseSet(
            this::loadModel
    );
}
```

The semantics are:

```
Does it not yet exist in value?

Create it.

Does it already exist?
Reuse it.
```

Why not just use "final"?

Imagine:

```
private final FraudModel model = loadModel();
```

The model is created in the startup.

Even if it is never used.

### Already with Stable Value:

```
private final StableValue<FraudModel> model = StableValue.of();
```


The model will only be created when someone actually needs it.

Java didn't create Stable Values ​​just for lazy loading.


### The goal is to guarantee:

1. Unique computation

Even if 100 threads perform:

```
model.orElseSet(...)
```

At the same time.

Only one thread will execute:
```
loadModel()
```

#### 2 - Secure Publishing

After the value is created:

```
FraudModel model
```


All threads see exactly the same instance.

Without:

```
volatile
```


```
synchronized
```

```
Lock
```

3. Logical Immutability
After being defined:

## Purpose of use

All these objects:

- They are expensive to upload;

- They are read thousands of times;

- They rarely change;

- They need to be shared across multiple threads.


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
