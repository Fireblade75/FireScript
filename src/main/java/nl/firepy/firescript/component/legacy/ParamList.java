package nl.firepy.firescript.component.legacy;

import nl.firepy.firescript.component.legacy.expr.EmptyComponent;
import nl.firepy.firescript.type.TypeConverter;
import nl.firepy.firescript.compiler.FireScriptParser;

import java.util.ArrayList;
import java.util.List;

public class ParamList extends EmptyComponent {

    private ArrayList<ParamListItem> params = new ArrayList<>();

    // public ParamList(List<FireScriptParser.ParamItemContext> paramItemContexts) {
    //     for(FireScriptParser.ParamItemContext item : paramItemContexts) {
    //         params.add(new ParamListItem(item.NAME().getText(), item.type().getText()));
    //     }
    // }

    public String asJasminList() {
        StringBuilder sb = new StringBuilder();
        for(ParamListItem item : params) {
            sb.append(TypeConverter.rawToJasmin(item.type));
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    public ArrayList<ParamListItem> items() {
        return params;
    }

    public class ParamListItem {
        private String name;
        private String type;

        public ParamListItem(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
