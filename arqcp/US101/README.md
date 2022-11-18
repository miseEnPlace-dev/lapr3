## Descrição da US 101
Pretende-se que seja implementada em Assembly uma função que gere números
pseudo-aleatórios, a ser usada na simulação dos sensores. É disponibilizada em C a função
pcg32_ramdom_r() e pretende-se que seja desenvolvida em Assembly uma função equivalente. É
também disponibilizado um exemplo de como ler de /dev/random para inicializar o gerador
indicado anteriormente.

@author Tomás Russo

## Funcionamento do gerador aleatório

### Incialização das seeds

Antes de utilizar o gerador aleatório, é necessário inicializar as seeds __state__ e __inc__. Estas seeds devem ser declaradas no escopo global, sendo do tipo __uint64_t__ (unsigned long).

Para isso, deve ser utilizada a função __get_value_from_dev_random()__ que lê um valor de /dev/random. O valor retornado deve ser utilizado para inicializar as seeds do gerador aleatório.

A função é acessível quando é incluido o ficheiro __dev_random.h__ no código.

    #include "dev_random.h"

    uint64_t state = 0;
    uint64_t inc = 0;

    int main(void) {
        state = get_value_from_dev_random();
        inc = get_value_from_dev_random();
        // ...
    }

### Utilização do gerador aleatório

Depois de inicializadas as seeds, é possível agora utilizar o gerador aleatório. 

O gerador devolve um valor aleatório, do tipo __uint32_t__ (unsigned int), entre 0 e 2^32-1. Para isso, deve ser utilizada a função __pcg32_random_r()__.

A função é acessível quando é incluido o ficheiro __random.h__ no código.

    #include "dev_random.h"
    #include "random.h"

    uint64_t state = 0;
    uint64_t inc = 0;

    int main(void) {
        state = get_value_from_dev_random();
        inc = get_value_from_dev_random();

        uint32_t random_value = pcg32_random_r();
        // ...
    }

### Exemplo de utilização

Para demonstrar a função geradora, o ficheiro main.c imprime 32 valores aleatórios. Para executar o programa, basta utilizar o comando __make run__ neste diretório.

    $ make run