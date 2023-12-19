INSERT INTO ppmtool.project (created_at, description, end_date, project_identifier, project_name, start_date,
                             updated_at, id)
VALUES ('2023-11-19 23:32:29.959000', 'Very cool project!', '2023-01-24 01:11:00', 'CPRJ', 'COOL',
        '2023-01-19 01:11:00', '2023-11-19 23:34:06.759000', 1);

INSERT INTO ppmtool.backlog (ptsequence, project_identifier, project_id, id)
VALUES (3, 'CPRJ', 1, 1);

INSERT INTO ppmtool.project_task (acceptance_criteria, create_at, due_date, priority, project_identifier,
                                  project_sequence, status, summary, update_at, backlog_id)
VALUES ('Be cool!', '2023-11-19 23:32:47.386000', '2023-11-23 01:00:00', 1, 'CPRJ', 'CPRJ-1', 'TO_DO',
        'This is a cool task', '2023-11-19 23:34:11.069000', 1),
       ('Be not cool :(', '2023-11-19 23:33:05.298000', '2023-11-19 01:00:00', 3, 'CPRJ', 'CPRJ-2', 'IN_PROGRESS',
        'This is not a cool task', '2023-11-19 23:34:17.583000', 1),
       ('Be the coolest :)!', '2023-11-19 23:33:23.971000', '2023-12-09 01:00:00', 2, 'CPRJ', 'CPRJ-3', 'DONE',
        'This is the coolest task', '2023-11-19 23:34:23.972000', 1);
