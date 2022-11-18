# Irrigation Controller

## Objetivo

Construir um objeto que simule o funcionamento de um controlador de rega.

## Requisitos

- O controlador deve conter um plano de rega para 30 dias;
- A qualquer momento, o controlador deve responder se está a regar ou não;
- Caso esteja a regar, deve retornar o setor e o tempo que falta para terminar a rega (em minutos).

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
