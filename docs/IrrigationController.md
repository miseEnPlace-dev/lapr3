# Irrigation Controller (US 306)

## Objetivo

Construir um objeto que simule o funcionamento de um controlador de rega.

## Critérios de Aceitação

- O controlador deve conter um plano de rega para 30 dias;
- A qualquer momento, o controlador deve responder se está a regar ou não;
- Caso esteja a regar, deve retornar o setor e o tempo que falta para terminar a rega (em minutos).

## Clarificações do Cliente

> \-

## Descrição

Para construir o objeto que representa o controlador, deve ser lido um ficheiro que contém um plano de rega.
Este ficheiro tem o seguinte formato:

```text
<horas de rega>
<parcela>,<duração>,<regularidade>
<parcela>,<duração>,<regularidade>
<parcela>,<duração>,<regularidade>
```

|                   |                                                                                             |
| :---------------- | :------------------------------------------------------------------------------------------ |
| `<horas de rega>` | As horas de início da rega por dia, separadas por vírgulas.                                 |
| `<parcela>`       | O identificador da parcela a regar.                                                         |
| `<duração>`       | A duração da rega em minutos.                                                               |
| `<regularidade>`  | A regularidade da rega que define os dias em que a rega acontece (todos, impares ou pares). |

\
Existe uma linha `<parcela>,<duração>,<regularidade>` para cada parcela a regar.

Só uma parcela pode estar a ser regada a qualquer momento. A ordem de rega é de acordo com a ordem das linhas no plano.

O campo `<regularidade>` pode ter os seguintes valores:

- `t` - a rega acontece todos os dias;
- `i` - a rega acontece nos dias ímpares;
- `p` - a rega acontece nos dias pares.
