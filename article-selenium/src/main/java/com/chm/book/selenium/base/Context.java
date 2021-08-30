package com.chm.book.selenium.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Context
{

    private static Map<String, Map<String, Object>> contextMap = new ConcurrentHashMap<>();
    private static Map<Long, String> threadIdToContextMap = new ConcurrentHashMap<>();

    public static Object get(String key)
    {
        long threadId = Thread.currentThread().getId();
        return get(key, threadId);
    }

    public static Object get(String key, long threadId)
    {
        if(threadId == -1) threadId = Thread.currentThread().getId();
        Object object = null;
        String contextTitle = threadIdToContextMap.get(threadId);

        Map<String, Object> objectMap = contextMap.get(contextTitle);
        object = objectMap.get(key);

        return object;
    }


    public static void put(String key, Object object)
    {
        long threadId = Thread.currentThread().getId();
        String contextTitle = threadIdToContextMap.get(threadId);
        if(contextTitle == null)
        {
            contextTitle = "webDriver";
            threadIdToContextMap.put(threadId, contextTitle);
            Map<String, Object> objectsMap = new HashMap<>();
            objectsMap.put(key, object);
            contextMap.put(contextTitle, objectsMap);

        }

        Map<String, Object> objectsMap = contextMap.get(contextTitle);
        objectsMap.put(key, object);

    }


    public static void remove(String key)
    {
        long threadId = Thread.currentThread().getId();
        String contextTitle = threadIdToContextMap.get(threadId);

        Map<String, Object> testMethodObjectsMap = contextMap.get(contextTitle);
        testMethodObjectsMap.remove(key);
    }

    private Context()
    {
        contextMap = new ConcurrentHashMap<>();
        threadIdToContextMap = new ConcurrentHashMap<>();
    }
}
