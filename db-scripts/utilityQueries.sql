-- ==========================================================
-- ||    UTILITY QUERIES TO SHORTEN MANUAL TESTING TIME    ||
-- ==========================================================

-- update exam of id 1 available_from time
UPDATE exam 
SET available_from = CURRENT_TIME() + INTERVAL 1 MINUTE,
state = 'HIDDEN',
duration = 30
WHERE id = 1;
SELECT id, name, available_from, state FROM myexaminer.exam;

UPDATE exam 
SET available_from = CURRENT_TIME() + INTERVAL 1 minute,
state = 'HIDDEN'
WHERE id = 2;
SELECT id, name, available_from, state FROM myexaminer.exam;

UPDATE exam 
SET available_from = CURRENT_TIME() + INTERVAL 30 SECOND,
state = 'CLOSED'
WHERE id = 2;
SELECT id, name, available_from, state FROM myexaminer.exam;

-- change exam state
UPDATE exam 
SET state = 'OPEN',
duration = 30
WHERE id = 1;
SELECT id, name, available_from, state FROM myexaminer.exam;
