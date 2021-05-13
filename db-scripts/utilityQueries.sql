-- ==========================================================
-- ||    UTILITY QUERIES TO SHORTEN MANUAL TESTING TIME    ||
-- ==========================================================

-- update exam of id 1 available_from time
UPDATE exam 
SET available_from = CURRENT_TIME() + INTERVAL 2 MINUTE,
state = 'HIDDEN'
WHERE id = 1;
SELECT id, name, available_from, state FROM myexaminer.exam;

UPDATE exam 
SET available_from = CURRENT_TIME() + INTERVAL 30 SECOND,
state = 'HIDDEN'
WHERE id = 1;
SELECT id, name, available_from, state FROM myexaminer.exam;
