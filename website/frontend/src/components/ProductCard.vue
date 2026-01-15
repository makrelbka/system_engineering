<template>
  <Card class="overflow-hidden transition-all hover:shadow-xl gap-0 border-2 border-black cursor-pointer group">
    <div class="relative h-64 w-full overflow-hidden">
      <img
        :src="product.image"
        :alt="product.name"
        class="h-full w-full object-cover transition-transform duration-300 group-hover:scale-105"
      />
    </div>
    <div class="flex flex-col items-start gap-3 p-4 bg-white">
      <div class="w-full">
        <h3 class="text-lg">{{ product.name }}</h3>
        <p class="text-sm text-gray-600">{{ product.description }}</p>
      </div>
      <div class="flex w-full items-center justify-between">
        <span class="text-xl">${{ product.price.toFixed(2) }}</span>
        <Button
          size="sm"
          class="bg-black hover:bg-gray-800"
          @click="$emit('add-to-cart', product)"
        >
          <Plus :size="16" class="mr-1" />
          В корзину
        </Button>
      </div>
    </div>
  </Card>
</template>

<script setup>
import { Plus } from 'lucide-vue-next'
import Button from './ui/Button.vue'
import Card from './ui/Card.vue'

defineProps({
  product: {
    type: Object,
    required: true
  }
})

defineEmits(['add-to-cart'])
</script>
