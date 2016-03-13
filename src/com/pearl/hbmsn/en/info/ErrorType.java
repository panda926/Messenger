package com.pearl.hbmsn.en.info;

public enum ErrorType {

	None,

    Fail_CoonectSocket,
    Fail_WaitSocket,
    Fail_ReceiveSocket,
    Fail_SendSocket,

    Fail_OpenDB,
    Fail_GetDB,
    Fail_WriteDB,

    Duplicate_Id,
    Invalid_Id,
    Invalid_Password,
    Duplicate_login,
    Duplicate_logout,
    Unknown_User,

    Duplicate_RoomId,
    Invalid_RoomId,
    Invalid_ChargeId,
    Invalid_GameId,
    Invalid_GameSource,

    Notenough_NewID,
    Notenough_Cash,
    Notenough_Point,
    Notallow_Chat,
    Already_Chat,
    Already_Serviceman,
    Notallow_NoServiceman,
    Notallow_Previlege,

    Live_Room,
    Full_Gamer,

    Exception_Occur,
    Notrespond_Server
}
