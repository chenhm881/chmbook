package com.chm.book.files.inteface;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IScan {
    void action(Integer projectId, Map<Integer, String> dirs,
                   String fileLocation, String action);
}
