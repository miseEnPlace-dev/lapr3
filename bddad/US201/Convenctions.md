In order to keep things consistent over the development of the data base these are some conventions every member should follow:

- Every data base object name should not have special characters beside '\_'
- SQL specific words should be written in capital case

**Naming Tables**

- When naming tables use Pascal Case (Ex: 'PascalCase')
- Use full names (no abbreviations)
- Use singular nouns
- Avoid using DBMS specific keywords (like 'Order')

**Naming Columns**

- When naming columns use Snake Case (Ex: 'snake_case')
- Column names should be specific and unique in his table
- Use long descriptive names instead of short and unclear abbreviations.
- Name must not be generic (Ex: use 'id_user´ instead of 'id')

**Naming Primary Keys**

- Primary Keys should be unique in the entire data base
- Should not be generic (Ex: use 'id_user´ instead of 'id')

**Naming Foreign Keys**

- Name should be similar to respective Primary Key
- If a foreign key is used in different tables, their names should be equals

**Naming Constraints**

- Use this format 'Prefix_ConstrainName'
- Prefixes:
  - Primary Keys - 'PK_TableName'
  - Foreign Keys - 'FK_TableName'
  - Unique Key (Combined Key) - 'UQ_TableName'
  - Check Constraint - 'CHK_TableName_ColumnName'
  - Default Constraint - 'DC_TableName_ColumnName'

**Functions**

- Use CamelCase
- Format: 'fn_Action'
- Format: 'pr_Procedure'
