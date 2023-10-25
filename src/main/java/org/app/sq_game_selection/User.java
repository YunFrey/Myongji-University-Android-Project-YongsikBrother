package org.app.sq_game_selection;
//다양한 사람들이 이용가능하게 추후 세션id를 DB계층 중간에 추가해야함

public class User {
    public String gamerNick;
    public String time;

    public User() {
        //더미생성자
    }

    public User(String gamerNick, String time) {
        this.gamerNick = gamerNick;
        this.time = time;
    }

    public String getGamerNick() {
        return gamerNick;
    }

    public String getTime() {
        return time;
    }

    public void setGamerName(String gamerNick) {
        this.gamerNick = gamerNick;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "입력된 내용구조 User{" + "gamerNick='" + gamerNick + '\'' +", timeStamp='" + time + '\'' +'}';
        //메모 : 최하단 내용은 가져올 수 있는데 User / 여기 데이터 / gamernick, timestamp 는 못가져오나..? child(User).getChildren 은 어떤 타입으로 리턴되지?
    }
}
