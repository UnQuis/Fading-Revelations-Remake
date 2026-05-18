import re, os

BUNDLES_DIR = "assets/bundles"
OLD_PREFIX = "me-"
NEW_PREFIX = "fading-revelations-patched-"

UNIT_RENAME = {
    "cerberian-antumbra": "cerberian-penumbra",
    "cerberian-atrax": "cerberian-hexathelid",
    "cerberian-arkyid": "cerberian-curtulus",
    "cerberian-crawler": "cerberian-straggle",
    "cerberian-dagger": "cerberian-bayonet",
    "cerberian-eclipse": "cerberian-veil",
    "cerberian-flare": "cerberian-spark",
    "cerberian-fortress": "cerberian-citadel",
    "cerberian-horizon": "cerberian-vista",
    "cerberian-mace": "cerberian-cudgel",
    "cerberian-reign": "cerberian-kaiser",
    "cerberian-scepter": "cerberian-baton",
    "cerberian-spiroct": "cerberian-nephila",
    "cerberian-toxopid": "cerberian-setosus",
    "cerberian-zenith": "cerberian-summit",
    "test": "lycosid",
    "cryo-crawler": "toruct",
    "fire-crawler": "reduct",
    "sps": "sps-strahl",
}

UNITS_RU = [
    ("aedes", "Эдес", "Маленький летающий юнит с базовым вооружением и создающим поток воздуха ротором."),
    ("heliaca", "Хелиака", "Маленький летающий юнит-разведчик с высокой скоростью."),
    ("armiger", "Армигер", "Улучшенный летающий юнит с усиленным ротором и ракетным вооружением."),
    ("arvens", "Арвенс", "Морской юнит поддержки с ремонтным лучом и восстанавливающим полем."),
    ("close-range", "Алопекс", "Быстрый летающий юнит ближнего боя с непрерывным лазером."),
    ("cromis", "Кромис", "Тяжёлый морской юнит с ракетной установкой и лечащим лазером."),
    ("procer", "Процер", "Летающий юнит поддержки с ракетными установками и мощным лазером."),
    ("sapling", "Саженец", "Летающий дрон-строитель с улучшенными показателями."),
    ("scofra", "Скофра", "Шестиногий юнит, стреляющий нефтью и огнём."),
    ("scorpio", "Скорпио", "Тяжёлый танк с мощным лазерным орудием."),
    ("aestiva", "Эстива", "Огромный морской юнит, спавнящий Альб."),
    ("arnux", "Арнукс", "Гигантский морской юнит с разрушительным лазером."),
    ("auratus", "Ауратус", "Шестиногий юнит, стреляющий водой, шлаком и ракетами."),
    ("ducalis", "Дукалис", "Тяжёлый летающий юнит с множеством лазеров."),
    ("kestrel", "Кестрел", "Летающий юнит с непрерывным лазером и электрическими разрядами."),
    ("onirion", "Онирион", "Летающий юнит с роторами, бомбами и ракетами."),
    ("plant", "Плант", "Тяжёлый летающий дрон-строитель с продвинутым вооружением."),
    ("springald", "Спрингалд", "Сверхтяжёлый танк с мощной артиллерией."),
    ("auctus", "Ауктус", "Длинный змееподобный юнит, топчущий здания."),
    ("onager", "Онагр", "Тяжёлый танк с мощным орудием."),
    ("culiseta", "Кулисета", "Большой летающий транспортный юнит."),
]

UNITS_EN = [
    ("aedes", "Aedes", "A small flying unit with basic weapons and a downdraft rotor."),
    ("heliaca", "Heliaca", "A small fast flying scout unit."),
    ("armiger", "Armiger", "An improved flying unit with enhanced rotor and missile armament."),
    ("arvens", "Arvens", "A naval support unit with repair beam and regen field."),
    ("close-range", "Alopex", "A fast close-range flying unit with a continuous laser."),
    ("cromis", "Cromis", "A heavy naval unit with missile launcher and healing laser."),
    ("procer", "Procer", "A flying support unit with missile pods and a powerful laser."),
    ("sapling", "Sapling", "A flying builder drone with improved stats."),
    ("scofra", "Scofra", "A six-legged unit firing oil and fire."),
    ("scorpio", "Scorpio", "A heavy tank with a powerful laser cannon."),
    ("aestiva", "Aestiva", "A huge naval unit that spawns Alba units."),
    ("arnux", "Arnux", "A giant naval unit with a devastating laser."),
    ("auratus", "Auratus", "A six-legged unit firing water, slag and missiles."),
    ("ducalis", "Ducalis", "A heavy flying unit with multiple lasers."),
    ("kestrel", "Kestrel", "A flying unit with a continuous laser and lightning arcs."),
    ("onirion", "Onirion", "A flying unit with rotors, bombs and missiles."),
    ("plant", "Plant", "A heavy flying builder drone with advanced weaponry."),
    ("springald", "Springald", "An ultra-heavy tank with powerful artillery."),
    ("auctus", "Auctus", "A long snake-like unit that stomps buildings."),
    ("onager", "Onager", "A heavy tank with a powerful cannon."),
    ("culiseta", "Culiseta", "A large flying transport unit."),
]


def get_new_key(line):
    """Given a line like 'unit.me-aestiva.name = ...', return the transformed key prefix (unit.fading-revelations-patched-aestiva)"""
    m = re.match(r'^(unit|block|item|liquid|status|planet|sector)\.(.*?)\.(name|description)\s*=', line)
    if not m:
        return None
    content_type = m.group(1)  # e.g. "unit"
    raw_name = m.group(2)      # e.g. "me-aestiva" or "cerberian-antumbra"
    
    if content_type != "unit":
        # For non-unit: just replace prefix
        if raw_name.startswith(OLD_PREFIX):
            return f"{content_type}.{NEW_PREFIX}{raw_name[len(OLD_PREFIX):]}"
        return f"{content_type}.{raw_name}"
    
    # For unit: replace prefix and potentially rename
    if raw_name.startswith(OLD_PREFIX):
        unit_name = raw_name[len(OLD_PREFIX):]
        # Check if this unit was renamed
        for old_name, new_name in UNIT_RENAME.items():
            if unit_name == old_name:
                return f"unit.{NEW_PREFIX}{new_name}"
        return f"unit.{NEW_PREFIX}{unit_name}"
    
    return f"unit.{raw_name}"


def process_bundle(filename, new_units):
    filepath = os.path.join(BUNDLES_DIR, filename)
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    lines = content.split('\n')

    # Collect all existing unit keys (after transformation)
    existing_keys = set()
    for line in lines:
        new_key = get_new_key(line)
        if new_key and new_key.startswith("unit."):
            existing_keys.add(new_key)

    out = []
    for line in lines:
        # Replace prefix and rename units
        new_key = get_new_key(line)
        if new_key and line.startswith(("unit.", "block.", "item.", "liquid.", "status.", "planet.", "sector.")):
            # Reconstruct line with new key
            m = re.match(r'^(unit|block|item|liquid|status|planet|sector)\..*?\.(name|description)\s*=\s*(.*)', line)
            if m:
                suffix = m.group(2)  # name or description
                value = m.group(3)
                line = f"{new_key}.{suffix} = {value}"
        out.append(line)

    # Add only truly missing units
    added = 0
    for name, display_name, desc in new_units:
        key = f"unit.{NEW_PREFIX}{name}"
        if key not in existing_keys:
            out.append(f"{key}.name = {display_name}")
            if desc:
                out.append(f"{key}.description = {desc}")
            added += 1
        else:
            print(f"  Skipped {name} (already exists)")

    result = "\n".join(out).rstrip('\n') + '\n'
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(result)
    print(f"Updated {filename} (added {added} new units)")


process_bundle("bundle_ru.properties", UNITS_RU)
process_bundle("bundle_de.properties", UNITS_EN)
process_bundle("bundle_es.properties", UNITS_EN)
process_bundle("bundle_uk_UA", UNITS_EN)
print("Done!")
