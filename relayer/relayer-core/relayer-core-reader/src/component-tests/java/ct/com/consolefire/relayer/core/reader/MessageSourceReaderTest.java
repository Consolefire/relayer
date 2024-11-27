package ct.com.consolefire.relayer.core.reader;

import com.consolefire.relayer.core.data.query.impl.StaticSelectQuery;
import com.consolefire.relayer.core.reader.MessageSource;
import com.consolefire.relayer.core.reader.MessageSourceReader;
import com.consolefire.relayer.core.reader.OutboundMessageSource;
import com.consolefire.relayer.model.Message;
import com.consolefire.relayer.model.OutboundMessage;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageSourceReaderTest {

    private DataSource dataSource;

    private MessageSource messageSource;
    private MessageSourceReader<UUID, OutboundMessage<UUID>> messageSourceReader;


    void setup() {
        String sql = "select * from outbound_messages where state = 'NEW'";

        messageSourceReader = new TestMessageSourceReader();
    }

    @Test
    void shouldReadMessagesFromSource() {
        Collection<OutboundMessage<UUID>> messages = messageSourceReader.read(messageSource);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

}
