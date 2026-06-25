#
# Update script db version 52 to db version 53
# Generate a table for the montly throughput of the system
# $Author: heller $
# $Revision: 1.2 $
#
#Missing indicies for the message table
#
CREATE INDEX idx_messages_senddateutc ON messages(senddateutc);
CREATE INDEX idx_messages_direction ON messages(direction);
CREATE INDEX idx_messages_senderid ON messages(senderid);
CREATE INDEX idx_messages_receiverid ON messages(receiverid);
CREATE INDEX idx_messages_userdefinedid ON messages(userdefinedid);
CREATE INDEX idx_messages_messagetype ON messages(messagetype);
