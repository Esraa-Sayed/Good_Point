# Generated by Django 3.1.7 on 2021-05-03 02:56

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('find_losts', '0008_auto_20210503_0451'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='lostpersonimage',
            unique_together={('id', 'image')},
        ),
    ]