# Generated by Django 3.1.7 on 2021-05-01 13:52

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('find_losts', '0004_auto_20210501_0251'),
    ]

    operations = [
        migrations.AlterField(
            model_name='foundobject',
            name='date',
            field=models.DateField(),
        ),
    ]