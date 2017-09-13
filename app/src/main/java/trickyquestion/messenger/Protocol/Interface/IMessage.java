package trickyquestion.messenger.Protocol.Interface;

import java.util.GregorianCalendar;

/**
 * Created by Zen on 09.09.2017.
 */
public interface IMessage {
    /**
     * Types of message
     */
    enum EMessageType {
        Text, File
    }

    /**
     * @return Who sent message
     */
    IUser from();

    /**
     * @return Type of message
     */
    EMessageType MessageType();

    /**
     * @return Message value
     * If message type is Text then return received text
     * If message type is File then
     * returns an string array consisting of two elements,
     * the first name and file extension,
     * and its second content is encoded by Base64
     */
    String[] value();

    GregorianCalendar ReceiveTime();
}
