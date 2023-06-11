SELECT DISTINCT developers.id, first_name, last_name, speciality_name, s.id, skls.id, skill_name
FROM developers
         LEFT JOIN speciality s on developers.id_spec = s.id
         LEFT JOIN devs_skills ds ON developers.id = ds.id_dev
         LEFT JOIN skills skls ON ds.id_skill = skls.id
WHERE developers.active_status_dev = true
  AND s.active_status_spec = true
  AND developers.id = ?;


SELECT developers.id from developers WHERE active_status_dev=true;

