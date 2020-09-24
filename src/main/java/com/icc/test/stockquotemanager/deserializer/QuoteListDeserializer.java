package com.icc.test.stockquotemanager.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.icc.test.stockquotemanager.entity.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class QuoteListDeserializer extends StdDeserializer<List<Quote>> {

    static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public QuoteListDeserializer() {
        this(null);
    }

    public QuoteListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Quote> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<Quote> quotes = new ArrayList<Quote>();
        JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
        for (Iterator<Map.Entry<String, JsonNode>> it = tree.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            try {
                Date date = dateFormat.parse(entry.getKey());
                BigDecimal quotation = new BigDecimal(entry.getValue().asText());
                Quote quote = new Quote(date, quotation);
                quotes.add(quote);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return quotes;

    }
}
