CREATE OR REPLACE VIEW cliente_view AS
  SELECT
      cl.id_cliente,
      cl.nome,
      (SELECT CASE WHEN enc.data_ultimo_incidente IS NULL AND c.valor_total_encomendas > 10000 THEN 'A'
                      WHEN enc.data_ultimo_incidente IS NULL AND c.valor_total_encomendas > 5000 THEN 'B'
                      ELSE 'C' END
      FROM cliente c
      LEFT JOIN (SELECT id_cliente, MAX(data_registo) AS data_ultimo_incidente
                FROM encomenda
                WHERE data_pagamento IS NULL OR data_pagamento > data_vencimento_pagamento
                GROUP BY id_cliente) enc ON enc.id_cliente = c.id_cliente
                WHERE c.id_cliente = cl.id_cliente) AS nivel,
      (SELECT NVL(TO_CHAR(MAX(data_vencimento_pagamento)), 'Sem incidentes à data') AS data_ultima_encomenda_nao_paga
      FROM encomenda
      WHERE (data_vencimento_pagamento < data_pagamento OR (data_vencimento_pagamento < sysdate AND data_pagamento IS NULL)) AND encomenda.id_cliente = cl.id_cliente
      GROUP BY id_cliente) AS data_ultimo_incidente,
      NVL((SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade) AS valor_pendente
      FROM produtoEncomenda pe, encomenda e
      WHERE pe.id_encomenda = e.id_encomenda AND e.id_cliente = cl.id_cliente AND
            (e.data_entrega IS NOT NULL AND e.data_pagamento IS NULL)
      GROUP BY id_cliente
      ),'0') AS ValorPendente,
      NVL((SELECT SUM((preco_unitario * (1 + iva / 100)) * quantidade) AS valor_pendente
      FROM produtoEncomenda pe, encomenda e
      WHERE pe.id_encomenda = e.id_encomenda AND e.id_cliente = cl.id_cliente AND
            (e.data_pagamento > sysdate - 365)
      GROUP BY id_cliente
      ), '0') AS VolumePago
  FROM Cliente cl;
