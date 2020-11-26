-- {   "ctime": 1606277316432,
--     "project": "taotao-cloud-backend",
--     "content": {
--         "login_id": "11111111111111",
--         "_track_id": 63276154,
--         "lib": {
--             "$lib": "js",
--             "$lib_method": "code",
--             "$lib_version": "1.15.16"
--             },
--         "distinct_id": "11111111111111",
--         "anonymous_id": "174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42",
--         "original_id": "174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42",
--         "type": "track_signup",
--         "event": "$SignUp",
--         "properties": {
--             "$screen_width": 1440,
--             "project": "taotao-cloud-backend",
--             "$screen_height": 900,
--             "$lib": "js",
--             "current_url": "http://localhost:3000/login",
--             "$device_id": "174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42",
--             "$latest_landing_page": "url???domain????????????",
--             "$title": "React Index",
--             "$timezone_offset": -480,
--             "$lib_version": "1.15.16",
--             "$latest_search_keyword": "url???domain????????????",
--             "$latest_traffic_source_type": "url???domain????????????",
--             "$url": "http://localhost:3000/login",
--             "$latest_referrer": "url???domain????????????",
--             "$latest_referrer_host": "????????????"
--             }
--         }
--     }
-- }


create external TABLE if not exists taotao_cloud_access_log
(
    project string,
    ctime   bigint,
    content  struct<login_id: string,
                    $track_id: bigint,
                    lib: map<string, string>,
                    distinct_id: string,
                    anonymous_id: string,
                    original_id: string,
                    type: string,
                    event: string,
                    properties: map<string, string>>
)
partitioned by (logday string)
row format serde "org.openx.data.jsonserde.JsonSerDe"
location "/taotao/cloud/access/log/sources";
