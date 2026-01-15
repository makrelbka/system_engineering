<template>
  <section class="py-16 px-4 bg-gray-50 border-b border-black">
    <div class="container mx-auto max-w-2xl">
      <div class="text-center mb-8">
        <h2 class="text-4xl mb-3">Кастомный букет</h2>
        <p class="text-gray-600">
          Оставьте заявку, и мы свяжемся с вами для обсуждения индивидуального букета
        </p>
      </div>
      
      <form @submit.prevent="handleSubmit" class="space-y-4 bg-white p-8 border-2 border-black rounded-lg">
        <div>
          <label for="name" class="block text-sm mb-2">
            Ваше имя *
          </label>
          <Input
            id="name"
            type="text"
            v-model="name"
            placeholder="Введите ваше имя"
            class="border-black"
            required
          />
        </div>
        
        <div>
          <label for="phone" class="block text-sm mb-2">
            Телефон *
          </label>
          <Input
            id="phone"
            type="tel"
            v-model="phone"
            placeholder="+7 (___) ___-__-__"
            class="border-black"
            required
          />
        </div>
        
        <div>
          <label for="message" class="block text-sm mb-2">
            Комментарий (необязательно)
          </label>
          <Textarea
            id="message"
            v-model="message"
            placeholder="Опишите желаемый букет, цветовую гамму, повод..."
            class="border-black min-h-[100px]"
          />
        </div>
        
        <Button 
          type="submit" 
          class="w-full bg-black hover:bg-gray-800"
        >
          Отправить заявку на обратный звонок
        </Button>
      </form>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useToast } from '../composables/useToast'
import Button from './ui/Button.vue'
import Input from './ui/Input.vue'
import Textarea from './ui/Textarea.vue'

const { toast } = useToast()

const name = ref('')
const phone = ref('')
const message = ref('')

const handleSubmit = async () => {
  if (!name.value || !phone.value) {
    toast.error('Пожалуйста, заполните имя и телефон')
    return
  }
  
  try {
    const apiUrl = import.meta.env.VITE_API_URL || '/api'
    const response = await fetch(`${apiUrl}/callback`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        name: name.value,
        phone: phone.value
      })
    })
    
    if (!response.ok) {
      throw new Error('Ошибка при отправке заявки')
    }
    
    toast.success('Заявка отправлена! Мы свяжемся с вами в ближайшее время.')
    name.value = ''
    phone.value = ''
    message.value = ''
  } catch (error) {
    toast.error('Не удалось отправить заявку')
  }
}
</script>
