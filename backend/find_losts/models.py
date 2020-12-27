from django.db import models
from user_account.models import User


# Create your models here.

class LostObject(models.Model):
    date = models.DateTimeField(null=False)
    city = models.CharField(max_length=35, null=False)
    user_id = models.ForeignKey(User, related_name='lost', on_delete=models.CASCADE, db_column='user_id')
    is_matched = models.BooleanField(default=False)

    class Meta:
        db_table = 'lost_object'


class LostPerson(models.Model):
    id = models.OneToOneField(LostObject, primary_key=True, on_delete=models.CASCADE, db_column='id')
    name = models.CharField(max_length=150)

    class Meta:
        db_table = 'lost_person'


class LostPersonImage(models.Model):
    id = models.OneToOneField(LostPerson, on_delete=models.CASCADE, db_column='id')
    image = models.BinaryField(unique=True)

    class Meta:
        db_table = 'lost_person_image'
        unique_together = (('id', 'image'),)


class LostItem(models.Model):
    id = models.OneToOneField(LostObject, primary_key=True, on_delete=models.CASCADE, db_column='id')
    type = models.CharField(max_length=20)
    color = models.CharField(max_length=20, null=True)
    brand = models.CharField(max_length=50, null=True)
    description = models.CharField(max_length=700)
    serial_number = models.CharField(max_length=100, null=True)

    class Meta:
        db_table = 'lost_item'


class FoundObject(models.Model):
    date = models.DateTimeField(null=False)
    longitude = models.DecimalField(max_digits=14, decimal_places=10, default=0.0)
    latitude = models.DecimalField(max_digits=14, decimal_places=10, default=0.0)
    city = models.CharField(max_length=35, null=False)
    user_id = models.ForeignKey(User, related_name='found', on_delete=models.CASCADE, db_column='user_id')
    matched_by = models.OneToOneField(LostObject, related_name='match', on_delete=models.CASCADE, db_column='matched_by')
    date_of_receiving = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = 'found_object'


class FoundPerson(models.Model):
    id = models.OneToOneField(FoundObject, primary_key=True, on_delete=models.CASCADE, db_column='id')
    name = models.CharField(max_length=150)

    class Meta:
        db_table = 'found_person'


class FoundPersonImage(models.Model):
    id = models.OneToOneField(FoundPerson, on_delete=models.CASCADE, db_column='id')
    image = models.BinaryField(unique=True)

    class Meta:
        db_table = 'found_person_image'
        unique_together = (('id', 'image'),)


class FoundItem(models.Model):
    id = models.OneToOneField(FoundObject, primary_key=True, on_delete=models.CASCADE, db_column='id')
    type = models.CharField(max_length=20)
    color = models.CharField(max_length=20, null=True)
    brand = models.CharField(max_length=50, null=True)
    description = models.CharField(max_length=700)
    serial_number = models.CharField(max_length=100, null=True)
    candidate = models.ManyToManyField(LostItem, related_name='candidate', db_table='candidate')

    class Meta:
        db_table = 'found_item'


