SELECT DISTINCT 
"Palabra".*,
"[GROUP_TABLE]"."WordID"
FROM "Palabra"
INNER JOIN "[GROUP_TABLE]" ON "[GROUP_TABLE]"."WordID" = "Palabra"."ID"
WHERE Level > 1
ORDER BY Level DESC;