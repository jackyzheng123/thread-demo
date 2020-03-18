--redis.call('set',KEYS[1],KEYS[2]);

local t1 = redis.call('get',KEYS[1]);
return t1;