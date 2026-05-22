package fadingrevelations.core;

import arc.func.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

public class CustomConsumePayloadDynamic extends Consume {
    public final Func<Building, PayloadStack[]> payloads;

    @SuppressWarnings("unchecked")
    public <T extends Building> CustomConsumePayloadDynamic(Func<T, PayloadStack[]> payloads) {
        this.payloads = (Func<Building, PayloadStack[]>)payloads;
    }

    @Override
    public float efficiency(Building build){
        float mult = multiplier.get(build);
        for(PayloadStack stack : payloads.get(build)){
            if(!build.getPayloads().contains(stack.item, Math.round(stack.amount * mult))){
                return 0f;
            }
        }
        return 1f;
    }

    @Override
    public void trigger(Building build){
        float mult = multiplier.get(build);
        for(PayloadStack stack : payloads.get(build)){
            build.getPayloads().remove(stack.item, Math.round(stack.amount * mult));
        }
    }

    @Override
    public void display(Stats stats){
    }

    @Override
    public void build(Building build, Table table){
        PayloadStack[][] current = {payloads.get(build)};

        table.table(cont -> {
            table.update(() -> {
                if(current[0] != payloads.get(build)){
                    rebuild(build, cont);
                    current[0] = payloads.get(build);
                }
            });

            rebuild(build, cont);
        });
    }

    private void rebuild(Building build, Table table){
        PayloadSeq inv = build.getPayloads();

        table.clear();
        int i = 0;

        for(PayloadStack stack : payloads.get(build)){
            table.add(new ReqImage(new Image(stack.item.uiIcon),
                () -> inv != null && inv.contains(stack.item, Math.round(stack.amount * multiplier.get(build)))
            )).padRight(8).left();
            if(++i % 4 == 0) table.row();
        }
    }
}
