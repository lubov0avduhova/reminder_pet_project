SELECT setval('reminder_id_seq', (SELECT MAX(id) FROM reminder));
