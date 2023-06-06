package com.buaa.pojo;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class TeamMember {
    int tId;
    int uId;
    int uPosition;
}
