SELECT COUNT(VerbID)
FROM [GROUP_TABLE]
INNER JOIN Verbo ON [GROUP_TABLE].VerbID = Verbo.ID
WHERE Level = 0;